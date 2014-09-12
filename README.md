# Foresight

Foresight is a simple weather app powered by the [Forecast.io](https://forecast.io) API. It's primary purpose is to be a playground for fancy Android libraries, and to experiment with 'modern' Android design.

## Some points of interest include:

* Data pipeline driven model layer powered by [RxJava](https://github.com/ReactiveX/RxJava).
* MVP architecture powered by dependency injection using [Dagger](http://square.github.io/dagger/).
* Globally applied custom face typeface using the [Caligraphy](https://github.com/chrisjenx/Calligraphy) library.
* Uses dependency injection to remove most `Fragment` and `Activity` boilerplate code. Powered by [Butter knife](http://jakewharton.github.io/butterknife/) with a small companion class for injecting layouts.
* Uses [Retrofit](http://square.github.io/retrofit/) and [Jackson](http://wiki.fasterxml.com/JacksonDownload) to provide an entirely declarative network transport layer.
* Uses the brand-new `RecyclerView` from Android L for forecast listings.
* Modular `Fragment`-based UI with strong separation of concerns.
* Has a clean-room reimplementation of the Java 8 `Optional` type which is used to prevent NPEs in model-presenter interactions.
* Uses functional idioms for transforming data.

## To Do:

* Implement UI integration testing using [Espresso](https://code.google.com/p/android-test-kit/wiki/Espresso).

## Requires

- Android Studio
- A Java 8 compiler, available with the Oracle JDK 8.
- Version 20 of the Android Build and Platform-tools.
- The Android Support Repository.
- The Google Repository Source.

# Architecture

## Basic Structure

* adapters: Contains the general use adapters used by the project.
* formatters: Contains single-static-method objects that are used to format forecast data before displaying it.
* functional: Contains all of the functional-idiom-related tools used by the project. Includes the `Optional` type, a static class containing utility methods for transforming lists, and copies of the interfaces introduced in Java 8 for passing around functions.
* graph: Contains the dependency injection module class, and all of the presenters used by the object.
* service: Contains the interface and classes used by Retrofit and Jackson to handle network transport.
* ui: contains the activities and fragments that make up the app user interface.
* util: Contains a helper class for producing consistent animations, and activity/fragment subclasses that automatically perform view and dependency injection.
* ForesightApplication.java: This classes responsibilities are minor. It initializes the Caligraphy library, and sets up the dependency object graph.

## The presenter architecture

### Responsibility

Foresight presenters do not map one-to-one with fragments and activities. Instead, Foresight gives one major responsibility to each presenter, and the presenters interact with each other through dependency injection and Rx signals. 

The presenters and their responsibliites are:

* ForecastPresenter: subscribes to location and user settings and produces forecast data through the `forecast` public subject.
* GeocodePresenter: A wrapper around the Android `Geocoder` class, it produces the location `name` displayed in the main UI, and also provides searching by location name for users who opt not to use GPS location.
* LocationPresenter: Wraps the Android `LocationManager` class. This class has two modes, determined by the user's settings:
	* A GPS mode in which the presenter will either use the user's most recent known location, or query for a fresh one.
	* A manual mode where the user has specified a single, static location they wish to see forecasts for.
* PreferencesPresenter: Wraps an Android `SharedPreferences` object and provides an Rx interface on top of the classic Android preferences system.

### Exposing state

All presenter state is exposed to interested parties through `public final ReplaySubject`s. A replay subject is an `Observable` reactive object that keeps a history of errors and values pushed to it. The presenters create replay subjects with a history of `1`, which allows the subjects to hold and playback the most recent state for all of the presenters. Whenever a state update occurs in one of the presenters, its pushed onto the replay subjects which then notify their subscribers. This means that views can use the same code for initial setup and later updates.


### Dependencies

Using `Observable` reactive objects for all state enables us to express multiple dependencies using compound observables. If a presenter requires multiple, disparate pieces of state to be available, it can simple create a compound observable of that state, and subscribe to both. This pattern can be observed in the `reload` method of the `ForecastPresenter`. The presenter requires the user's location and their preferred unit system in order to complete a network request, so it creates a compound observable for those two bits of state and then fires off the network request whenever they become available.

### Updates

All presenters are asynchronously updated. All of the presenters will perform an update immediately upon construction. Future updates are then a consequence of user interaction. The `ForecastPresenter` will automatically be updated by the `HomeActivity` if the user resumes the app after 15 minutes has passed. Any changes to the app's settings through the `SettingsActivity` will result in a `ForecastPresenter` update.

# License

Copyright (c) 2014, Live Nation Worldwide, Inc. All rights reserved.

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT
OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.