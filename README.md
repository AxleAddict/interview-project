# Array Comparison: Debugging Exercise

A short maintenance assignment in an unfamiliar Java service. Work in the existing
structure, explain your investigation, and add regression tests for your changes.
No database, external service, authentication, or UI is needed.

## Setup

- JDK 21 and Maven 3.9 or newer.
- Open the project as a Maven project. Enable Lombok/annotation processing if your
  editor requires it; the Maven compiler is the build authority.
- Allow about 75 minutes. Ask the interviewer about permitted tools before starting.

On macOS, select Java 21 if Maven uses another JDK:

```sh
export JAVA_HOME=$(/usr/libexec/java_home -v 21)
```

```sh
mvn clean test
mvn spring-boot:run
```

The starting project does not build successfully. That is the first task, not a
setup problem. Resolve build errors before investigating runtime behavior.

## Existing API Contract

`POST /api/arrays/common-elements`

```json
{
  "left": [4, 5, 6, 12, 2, 6, 19, 1],
  "right": [7, 19, 3, 6]
}
```

Expected HTTP 200 response:

```json
{
  "commonElements": [6, 19]
}
```

- Return distinct values present in both arrays.
- Preserve first-occurrence order from `left`. Do not sort the result.
- Do not modify either input list.
- Both arrays are required. Empty arrays are valid and produce an empty result.
- Elements must be JSON integers in the signed 32-bit range. Negative numbers and
  zero are valid; null elements, numeric strings, and decimals are not.
- The current maximum is 10,000 elements per array.
- Missing/null arrays, invalid values, oversized arrays, malformed JSON, and
  unknown fields return HTTP 400. The error response body is not part of the task.

```sh
curl -i http://localhost:8080/api/arrays/common-elements \
  -H 'Content-Type: application/json' \
  -d '{"left":[4,5,6,12,2,6,19,1],"right":[7,19,3,6]}'
```

Set `PORT` to use another port, for example `PORT=8081 mvn spring-boot:run`.
The optional `requests.http` file contains additional requests.

## Tasks

### 1. Restore the Build

Fix the compilation issue and start the application. Explain what failed and
why your change addresses it. Avoid unrelated refactoring.

### 2. Correct the JSON Behavior

The sample request does not produce the documented response. Reproduce the
problem, locate its root cause, and fix it without changing the API contract.
Add a regression test at the boundary responsible for the defect.

### 3. Extend the Behavior

Product request:

> Some clients need repeated matches, not just unique values. Add an optional
> `matchMode`, keep existing clients working, and support larger lists.

Clarify the requirement with the interviewer before implementation. Record the
agreed behavior and examples, then implement it in the same endpoint. Consider
correctness, backward compatibility, input validation, and algorithmic cost.
Update a pre-existing test only when its contract has explicitly changed.

## Handover

Run `mvn clean verify`. Summarize the root causes, agreed requirements, tests,
time/space complexity, and anything you did not complete. Do not suppress failing
tests or remove assertions to make the build green.

## Layout

```text
src/main/java/com/example/interview/
  InterviewApplication.java
  controller/ArrayComparisonController.java
  bean/ArrayComparisonRequest.java
  bean/ArrayComparisonResponse.java
  service/ArrayComparisonService.java
  service/impl/ArrayComparisonServiceImpl.java
src/test/java/com/example/interview/
  controller/ArrayComparisonControllerTest.java
  service/ArrayComparisonServiceImplTest.java
```