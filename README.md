# regurgitator-core-config-json

regurgitator is a modular, light-weight, extendable java-based processing framework designed to 'regurgitate' canned or clever responses to incoming requests.

See more at [regurgitator-all](http://github.com/talmeym/regurgitator-all)

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

| value | meaning |
| :--- | :--- |
| true | child steps given new blank message object |
| with-parameters | child steps given new message object with only the parameters context of the original |
| with-session | child steps given new message object with only the session context of the original |
| with-parameters-and-session | child steps given new message object with the parameters and session context of the original |
