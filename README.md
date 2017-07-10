# RxBus

[ ![Download](https://api.bintray.com/packages/kevalpatel2106/maven/rx-bus/images/download.svg) ](https://bintray.com/kevalpatel2106/maven/rx-bus/_latestVersion) [![Build Status](https://travis-ci.org/kevalpatel2106/rxbus.svg?branch=master)](https://travis-ci.org/kevalpatel2106/rxbus)

#### Implementation of event bus using Rx for Android.

## Gradle dependency: 
- Add below dependency into your build.gradle file.

```compile 'com.kevalpatel2106:rxbus:1.0'```

## How to use?
- To post an event,

```
RxBus.getDefault().post(new Event(YOUR_OBJECT_TO_PASS));
```

- To register the event,

```
//Register the event to the bus
RxBus.getDefault().register(Point.class)
        .doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(@NonNull Disposable disposable) throws Exception {
                mDisposable = disposable;   //Hold the disposable.
            }
        })
        .subscribe(new Consumer<Event>() {
            @Override
            public void accept(@NonNull Event event) throws Exception {
              YOUR_OBJECT = (YOUR_OBJECT)    EVENT.GEToBJECT();
              
              //Do something with the object.
            }
        });
```

- To unregister the event, dispose the disposable received in `onDispose()`.

```
//Unregister from the event bus.
if (mDisposable != null) mDisposable.dispose();
```
## Demo

![sample](/data/sample.gif)

- You can download the sample apk from [here](/data/sample.apk).

## Contribute:
- Any pull request is most welcome.

**Simple 3 step to contribute into this repo:**
1. Fork the project.
2. Make required changes and commit.
3. Generate pull request. Mention all the required description regarding changes you made.

## Questions:
Hit me on twitter [![Twitter](https://img.shields.io/badge/Twitter-@kevalpatel2106-blue.svg?style=flat)](https://twitter.com/kevalpatel2106)

## License
Copyright 2017 Keval Patel

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.

