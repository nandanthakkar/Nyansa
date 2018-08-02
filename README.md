# Nyansa

Solution for Programming Exercise:

m is number of URL hits.
d is number of days.
a is maximum number of unique URLs visited on a day.

### Runtime complexity:
##### O(m) for reading each line of the file
##### O(m) for adding or checking dates in HashMap to add/update hit count (adding or checking date in HashMap is O(1) and we perform that operation m times because there are m lines)
##### O(m) for initializing or updating hit count (each time we add or check date in HashMap we also initialize (by putting new URL and count = 1) or update hit count in HashMap which is stored as a value in another HashMap whose key is date)
##### O(d\*log(d)) for sorting dates in the ascending order.
##### O(d\*(a\*log(a))) for sorting in descending order of URL hit count per day (sorting takes a\*log(a) and we perform that operation for d times)
#### As m >> d\*a, runtime complexity is O(m)

### Space Complexity is O(d\*a) 



