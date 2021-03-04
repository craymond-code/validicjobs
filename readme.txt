# Validic City Languages Example

The following code creates the requested functionality of "indicating programming language trends across [cities] based on the information sourced from GitHub Jobs."
It should also demonstrate basic competency of working with RESTful APIs, common industry tools, software design, and general software development best practices.

## Considerations
Initially I was tempted to create a small webserver that would fetch and the re-present this information with its own REST endpoint, but I see that the stated expected timeline is "about 6 hours." Instead I won't overbuild beyond the stated requirements and limit the scope of this example to a standalone CLI application and focus on demonstrating best practices in consuming
RESTful APIs and Unit tests instead of building them. The information will be presented as simple text.

Assume in a production scenario this would be a service component and small chunk of business logic embedded within a larger application feeding this information downstream via it's own RESTful APIs once the data has been consolidated.

I'm beginning this work on Wednesday evening, and expect to turn it in tomorrow morning.

Target unit tests:
- Assert a status check returns a 200
- Assert a made up language with 0 results does not fail
- Assert we can find at least one kotlin job


## Running the code
Very simple maven based project. To install maven see https://maven.apache.org/guides/getting-started/.
To run this code, execute `mvn install` and `java -jar validicjobs-1.0-runnable.jar`


## Challenges
I didn't expect to get a code challenge so quickly so the machine I did this on was not set up for basic java dev. I ended up spending a little over an hour doing simple java/mvn boilerplate stuff I haven't had to do from scratch in a while. This wasn't a challenge necessarily but it took more time than expected.

## Areas of code I'm most proud of
Although I didn't do anything particularly fancy with it, the flexibility of the Jackson ObjectMapper is phenomenal, and it has been my friend when dealing with many an inconsistent API - both JSON and XML based. Simple annotations and a date formatter and I don't have to think about the JSON anymore.

## Areas of code I'm least proud of
There's not a single interface or other layer of abstraction beyond package separation. In any codebase of real size I would build my interfaces first and develop to them. Services should be de-coupled from business logic as much as possible so they can be swapped out if necessary.
Additionally if we get a non-200 from the Github API I'm just bubbling up an unhandled HTTP exception. Not ideal for demonstrating competence with RESTful APIs but in the "real world" typically I do prefer allowing an HTTP exception to bubble up into business logic to some degree. I like my service to be ignorant to where it's being used, do its best to get/post/del the data, report the health of the upstream system via exceptions that bubble up, and any throttling/alerts that the health of an API is not 100% should be tunable based on the situation.

## Tradeoffs
I wanted to ensure I demonstrated I can throw some dependencies together and won't re-invent the wheel (I'm not manually parsing any json) but also that I can logically structure my code and not simply configure all-in-one frameworks. I worked on this in 2, 3 hours chunks wednesday night and thursday morning, and if timing had been better I would have reached out for more clarification on what the dev team was looking for. I know often teams are hoping to see a candidate reach out to clarify requirements (obviously important in the real-world) and if I did this over at a different time I would reach out as soon as the email came in. It's been a busy week and Cathy indicated returning it in the next few days would be fine, but I did try to limit myself to 6 hours as the instructions laid out. Additionally one giant check-in is not really how I use source control on a real codebase.

## Where to go to move this to a production-ready environment
My steps would be as follows:
1. Create interfaces to formally separate service vs business logic functionality
2. Add in retry and throttling logic when requests fail so I don't hammer the API
3. Start building data models that could be hooked up to some sort of persistence layer


