# regurgitator-core-config-json

regurgitator is a modular, light-weight, extendable java-based processing framework designed to 'regurgitate' canned or clever responses to incoming requests.

start your reading at [regurgitator-all](http://github.com/talmeym/regurgitator-all)

## xml configuration of regurgitator core functionality

below is an example of an xml configuration file for regurgitator:

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
            "id": "my-step-1", 
            "source": "response" 
        }
    ]
}
```

## steps

### sequence

a sequence is a step that executes a series of child steps, one after another in order

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
            "id": "my-step-1",
            "source": "response"
        }
    ]
}
```

by default, when each child step executes, it is passed the same message object that the sequence received. it is possible to "isolate" a sequence's child steps from the data contained in the message object, by giving the sequence an isolation level. this prevents a child step from receiving data it shouldn't see or that it won't need.

```json
{
    "kind": "sequence",
    "id": "my-sequence",
    "isolate": "with-parameters", 
    "steps": [
        {
            "kind": "create-parameter",
            "id": "my-step-1",
            "name": "response",
            "value": "this is the response"
        },
        {
            "kind": "create-response",
            "id": "my-step-1",
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

each rule has one or more conditions that must be satisfied to make the rule pass. each condition evaluates the value of a parameter within the message object, specified by the ``source`` attribute, against an operand. each condition has a ``condition behaviour`` that dictates the manner in which the value is evaluated against the operand. the example above uses the ``equals`` condition behaviour.

the behaviour of a condition can be specified as a behaviour object of the parent condition (as opposed to being an attribute) as shown below:

```json
	...
    "rules": [
      {
          "step": "special-step",
          "conditions": [
              {
                  "source": "parameters:special",
                  "behaviour": {
                      "kind": "equals",
                      "value": "special"
                  }
              }
          ]
      }
    ]
	...
```

this allows custom condition behaviours to have attributes besides the operand (in the example above, "true") which is always the value attribute of the child object.

there are 3 core condition behaviours:

| value | behaviour |
| :--- | :--- |
| equals | checks the parameter value equals the operand |
| exists | checks the parameter value exists (read as 'parameter exists') |
| contains | checks the parameter value contains the operand |
