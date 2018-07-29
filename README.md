# Nyansa

Solution for Programming Exercise:

m is number of URL hits.
n is number of unique URLs.
d is number of days.
a is average number of unique URLs visited each day.

##### Runtime complexity: O(m) for reading each line of the file + O(m\*log(m)) for adding or checking date + O(m) for adding or updating hit count + O(d\*(a\*log(a))) for sorting in descending order of URL hit count per day.
### As m >= d\*a, runtime complexity is O(mlog(m))

### Space Complexity is O(d\*a) 



