Wi-Fi Logger:

This Android app measures and records the Received Signal Strength Indicator (RSSI) of every visible Wi-Fi access point (AP) at atleast three user-defined locations, storing each as a 100-sample matrix. It then lets you compare how signal ranges differ across those locations.


Features:
- Alteast Three Custom Locations
- On launch, add up to three named locations (e.g. “Living Room,” “Office,” “Backyard”).
- Each location is stored in Room and can be deleted (which also removes its scan data).
- 100-Sample RSSI Matrix
- Tapping Scan for a location runs 100 consecutive Wi-Fi scans (≈3 s apart).
- Each AP’s RSSI is recorded; if an AP disappears mid-run its slot is zero-filled (assumption).
- At completion, every AP → 100-element RSSI matrix is saved.


Live & Stored Data View
- Live Data: see the real-time build-up of each AP’s 10×10 matrix as scans arrive.
- Stored Data: browse the last saved matrix for each AP, per location.
Lazily lists live updates and stored snapshots side by side, with Reset/Store controls fixed at the bottom.


Built-in buttons to Reset (zero out or delete orphaned scans) or Store Live Data on demand.


Range Comparison
- Select all three locations, then tap Compare Selected.
- The app finds APs common to every location and for each:
- Draws a line chart of RSSI vs. sample index (1–100), one colored curve per location.
- Displays an overall bar chart of each location’s maximum RSSI.
- Prints min/max RSSI for each location and highlights the “best” spot.




How It Works
Room Database holds two entities:
- Location (id, name)
- Scan (locationId, bssid, ssid, timestamp, rssiMatrix)
- WifiScanManager (singleton) launches a coroutine per location that:
- Calls wifiManager.startScan(), delays ~3 s, reads scanResults.
- Updates StateFlow streams for scan count and AP→List<RSSI>.
- Compose UI observes these flows and renders matrices, buttons, and charts.


Getting Started
Clone this repository into Android Studio.
Download the gradle files.
Grant Location permissions when prompted.
Add three locations, tap Scan, wait for 100 scans.
Explore Stored & Live Data, then select all three locations and Compare Selected.