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
        { "kind": "create-parameter", "id": "my-step-1", "name": "response", "value": "this is the response" },
        { "kind": "create-response", "id": "my-step-1", "source": "response" }
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

when a sequence executes each of it's steps, it passed on the message object that was passed to it. you can "isolate" a sequence's child steps from the data contained in the message received by the sequence, by giving the sequence an isolation level. this prevents the child steps from receiving data they shouldn't or that they won't need.

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
| true | child steps given new blank execution message |
| with-parameters | child steps given new execution message with only the parameters context of the original |
| with-session | child steps given new execution message with only the session context of the original |
| with-parameters-and-session | child steps given new execution message with the parameters and session context of the original |
