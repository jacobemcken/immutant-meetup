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

[1]: http://immutant.org/tutorials-1x/installation/index.html


## License

Copyright © 2014

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
