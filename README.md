# immutant-meetup

This repository will be the base for the talk at the next [Copenhangen Clojure Meetup][].

As of this writing version 1.1.3 is the latest stable version.

[Copenhangen Clojure Meetup]: http://www.meetup.com/Copenhagen-Clojure-Meetup/events/190043432/


## Steps


### Step 1

This repo was created by:

    lein new immutant-meetup

### Step 2 - Install Immutant

Add the following to the ´~/.lein/profiles.clj´ file:

    {:user {:plugins [[lein-immutant "1.2.1"]]}}

Then install using `lein`:

    lein immutant install

This will install the latest stable version.

More information about [installing][1] (including alternative ways) on
the official website.


## Step 3 - Spin it up... but first

Immutant is now ready to start but let's give it something to show off
before we move on. A good place to start is with the web component.

For that it needs a handler for the app context, and even though
Immutant supports using `:ring` (from lein-ring) in `project.clj`, the
`immutant.init` namespace will be used to prepare the way for
initializing other stuff than just a web handler.

Start Immutant using:

    lein immutant run

After seeing `JBoss AS 7.2.x.slim.incremental.16 "Janus" started in`
in the console Immutant has starter but the application hasn't been
deployd to the server yet.

This is done by doing (from within the project folder):

    lein immutant deploy

On the console Immutant will tell us:

    Starting deployment of "immutant-meetup.clj"

and after a short while:

    Deployed "immutant-meetup.clj"

Now we can access the application on http://localhost:8080/immutant-meetup/

The order in which `run`/`deploy` is executed is irreleveant.

To stop a running Immutant instance press `Ctrl - c`.
When Immutant is restarted all applications are (re-)deployed.


[1]: http://immutant.org/tutorials-1x/installation/index.html


## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
