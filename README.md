# regurgitator-core-json

regurgitator is a modular, light-weight, extendable java-based processing framework designed to 'regurgitate' canned or clever responses to incoming requests; useful for mocking or prototyping services.

start your reading here: [regurgitator-all](http://github.com/talmeym/regurgitator-all#regurgitator)

## json configuration of regurgitator

below is an example of a json configuration file for regurgitator:

```json
{
    "kind": "sequence",
    "id": "my-sequence",
    "steps": [
        { 
            "kind": "create-parameter", 
            "id": "my-step-1", 
            "name": "response", 
            "value": "this is the response" 
        },
        { 
            "kind": "create-response", 
            "source": "response" 
        }
    ]
}
```

#### object kinds and step ids

all objects in a regurgitator json configuration have a mandatory ``kind`` property. this is a mandatory identifier of the objects type and is used by regurgitator to load the correct step / construct for the object.

all steps in a regurgitator configuration can be given an ``id`` property. ids can be used for identifying which step to run next (see [decision](https://github.com/talmeym/regurgitator-core-json#decision), below) and therefore must be unique. if no id property is given for a step, a system-generated one will be assigned to it at load time, combining the type of the step with a 4 digit randon number, eg: ``create-parameter-6557``

## core steps in json

### sequence

a sequence is a step that executes a series of child steps, one after another in order

```json
{
    "kind": "sequence",
    "steps": [
        {
            "kind": "create-parameter",
            "name": "response",
            "value": "this is the response"
        },
        {
            "kind": "create-response",
            "source": "response"
        }
    ]
}
```

by default, when each child step executes, it is passed the same message object that the sequence received. it is possible to "isolate" a sequence's child steps from the data contained in the message object, by giving the sequence an isolation level. this prevents a child step from receiving data it shouldn't see or that it won't need.

```json
{
    "kind": "sequence",
    "isolate": "with-parameters", 
    "steps": [
        {
            "kind": "create-parameter",
            "name": "response",
            "value": "this is the response"
        },
        {
            "kind": "create-response",
            "source": "response"
        }
    ]
}
```

isolation has 4 settings:

| value | child step receives |
| :--- | :--- |
| true | new blank message object |
| with-parameters | new message object containing the parameters context of the original message |
| with-session | new message object containing the session context of the original message |
| with-parameters-and-session | new message object containing parameters and session of the original message |

### decision

a decision executes one or more child steps, using ``rules`` and ``conditions`` to determine which steps to run

```json
{
    "kind": "decision",
    "steps": [
        {
            "kind": "create-response",
            "id": "default-response",
            "value": "this is the default response"
        },
        {
            "kind": "create-response",
            "id": "special-response",
            "value": "this is the special response"
        }
    ],
    "default-step": "default-step",
    "rules": [
        {
            "step": "special-step",
            "conditions": [
                {
                    "source": "parameters:special",
                    "equals": "special"
                }
            ]
        }
    ]
}
```

upon execution a decision evaluates all of its rules to see which pass. it then uses its ``rules behaviour`` to determines which of the passed rules should have their corresponding step executed. the default rules behaviour is ``FIRST_MATCH`` whereby the first rule that passes provides the step to be executed.

each rule has one or more conditions that must be satisfied to make the rule pass. each condition evaluates the value of a parameter within the message object, specified by the ``source`` property, against an operand. each condition has a ``condition behaviour`` that dictates the manner in which the value is evaluated against the operand. the example above uses the ``equals`` condition behaviour, specified as a property named 'equals'.

the behaviour of a condition can also be specified as a 'behaviour' property, either as a string:

```json
	...
        "default-step": "default-step",
        "rules": [
	    {
	        "step": "special-step",
	        "conditions": [
	            {
	                "source": "parameters:special",
	                "value": "special",
	                "behaviour": "equals"
	            }
	        ]
	    }
	]
	...
```

or as an object:

```json
	...
        "default-step": "no-id-found",
        "rules": [
	    {
	        "step": "found-id",
	        "conditions": [
	            {
	                "source": "parameters:xml",
	                "value": "/rg:config/@id",
	                "behaviour": {
	                     "kind": "contains-xpath",
	                     "namespaces": {
	                     	"rg": "http://url.com"
	                     }
	                }
	            }
	        ]
	    }
	]
	...
```

which allows some condition behaviours to have properties besides the operand (in the example above, the namespaces of the xpath specified in the value property).

there are 5 core condition behaviours:

| value | behaviour |
| :--- | :--- |
| equals | checks the parameter value equals the operand |
| equals-param | checks the parameter value equals the value of another parameter |
| exists | checks the parameter value exists (read as 'parameter exists') |
| contains | checks the parameter value contains the operand |
| contains-param | checks the parameter value contains the value of another parameter |
