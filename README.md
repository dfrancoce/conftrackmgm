# conftrackmgm

Little program to manage the talks of a conference. It reads an input file from the resources folder and it fits the talks into the time constraints of the day.

## Constraints
* The conference has multiple tracks each of which has a morning and afternoon session.
* Each session contains multiple talks.
* Morning sessions begin at 9am and must finish by 12 noon, for lunch.
* Afternoon sessions begin at 1pm and must finish in time for the networking event.
* The networking event can start no earlier than 4:00 and no later than 5:00.
* No talk title has numbers in it.
* All talk lengths are either in minutes (not hours) or lightning (5 minutes).
* Presenters will be very punctual; there needs to be no gap between sessions.

## Example

The `TrackServiceTest` renders out the talks already fit into the time constraints of the day

### Test input
```
Writing Fast Tests Against Enterprise Rails 60min
Overdoing it in Python 45min
Lua for the Masses 30min
Ruby Errors from Mismatched Gem Versions 45min
Common Ruby Errors 45min
Rails for Python Developers lightning
Communicating Over Distance 60min
Accounting-Driven Development 45min
Woah 30min
Sit Down and Write 30min
Pair Programming vs Noise 45min
Rails Magic 60min
Ruby on Rails: Why We Should Move On 60min
Clojure Ate Scala (on my project) 45min
Programming in the Boondocks of Seattle 30min
Ruby vs. Clojure for Back-End Development 30min
Ruby on Rails Legacy App Maintenance 60min
A World Without HackerNews 30min
User Interface CSS in Rails Apps 30min
```

### Test output
```
Track 1:
09:00 AM Writing Fast Tests Against Enterprise Rails  60min
10:00 AM Overdoing it in Python  45min
10:45 AM Lua for the Masses  30min
11:15 AM Ruby Errors from Mismatched Gem Versions  45min
12:00 PM Lunch
01:00 PM Rails Magic  60min
02:00 PM Ruby on Rails Legacy App Maintenance  60min
03:00 PM Pair Programming vs Noise  45min
03:45 PM Common Ruby Errors  45min
04:30 PM Woah  30min
05:00 PM Networking Event

Track 2:
09:00 AM Communicating Over Distance  60min
10:00 AM Programming in the Boondocks of Seattle  30min
10:30 AM A World Without HackerNews  30min
11:00 AM Sit Down and Write  30min
11:30 AM Ruby vs. Clojure for Back-End Development  30min
12:00 PM Lunch
01:00 PM Accounting-Driven Development  45min
01:45 PM Rails for Python Developers  lightning
01:50 PM Ruby on Rails: Why We Should Move On  60min
02:50 PM User Interface CSS in Rails Apps  30min
03:20 PM Clojure Ate Scala (on my project)  45min
05:00 PM Networking Event
```

### Requirements and usage
Maven is required in order to test this program. To execute go to the root of the project:

```
mvn test
```
