Prince Kumar (2022378)


MC Assignment-2




Readme:
Flight Tracker App
github repo = https://github.com/Prince22378/CSE535-MobileComputing_Assignments/tree/main/Assignment-2


How to run: 
> clone the project
> open it in android studio
> make gradle/sync gradle
> run it in emulator/mobile


It’s a Jetpack Compose-based Android app to search, select, and track flights both live(Ques1) and historical(Ques2).


Screens Overview
-  FlightSearchScreen.kt
    Purpose:
Search for flights between two airports and select up to 3 for tracking.
     Key Features:
- take Input for departure and arrival IATA codes
- fetches flights using AviationStack API
- displays all the flights in selectable card format with lazyoading implemented
- allows selection of up to 3 flights only
- on confirmation, saves selected flights to local Room DB (SelectedFlightFull)
- automatically replaces old tracked flights with the new one.
- we can navigate to TrackedFlightsScreen via top right icon (menu bar type)
- Toggle for light/dark theme


   UI Enhancements:
- Clean card layout with icons and text alignment
- Flight card highlights on selection
- "Need 3 flights for confirmation" if less than 3 the tracking wouldn’t be done and he’ll 
   have a option to reset/restart.
- Shows fallback message if flights not found
- Inputs/Search button disable/enable flow based on selection state
- Toggle for light/dark theme


-  TrackedFlightsScreen.kt
     Purpose:
- Displays every details including avg. delay, etc. of tracked flights.
     Key Features:
- Shows full flight details including departure/arrival data


    Computes:
- Average journey time (actual arrival - actual departure)
- Average departure delay
- Average arrival delay
- Auto-refreshes data once per day so only 1 API call requires perday
- Deletes flights older than 7 days if no new flights are been tracked.


    UI Improvements:
- Each flight shown in a Card
- Grouped data: Departure, Arrival, Journey stats
- Better font hierarchy and visual spacing


 -  FlightTrackerScreen.kt (Ques 1)
     Purpose:
- Live flight tracking using flight number (e.g., UA849)
    Key Features:
- User enters flight no. then start tracking the flight live. Refreshes live data every 60 
  seconds
    
    Displays:
- Airline name, Status, Departure & Arrival details, Live location, speed, altitude, and 
  direction (if available)
- Toggle for light/dark theme


- FlightViewModel.kt
   Purpose:
         - Manages state and periodic polling for live flight tracking
   Exposes:
- Current FlightData, isTracking flag, lastUpdated time, hasFetchedOnce for error 
   fallback and many other memory val that are being used by FlightTrackerScreen.kt & 
   FlightSearchScreen


- FlightApiService.kt
   Purpose:
- Retrofit API interface for AviationStack.
   Endpoints:
- getFlight() → single flight data
- getFlightsBetween() → flights between two airports codes or can say IATA codes
- Provides ApiClient.api with logging for debug


Room DB Overview
- FlightDatabase.kt
- Room singleton for the app
- Exposes DAO: FlightDao


- FlightDao.kt
   Handles:
- Insert/delete SelectedFlightFull
- Insert FlightHistory
- Delete flights older than 7 days
- Track updates with TrackedFlight


-  FlightHistory.kt
- Stores logs per flight per day


   Tracks:
- Scheduled vs Actual times
- Departure/Arrival delays
- Used for calculating the average values for delays, etc. in TrackedFlightsScreen