# Detailed Explanation of Guava RateLimiter's Throttling Mechanism

Throttling is one of the three effective methods for protecting a high concurrency system. The other two are respectively caching and downgrading. Throttling is used in many scenarios to limit the concurrency and number of requests. 

The purpose of throttling is to protect the system by restricting concurrent access or requests or restricting requests of a specified time window. After the threshold is exceeded, denial of service or traffic shaping is triggered. 

Common throttling methods are 
* Limit the concurrency - for example you can limit the size of the database connection pool and thread pool. 
* Limit the instantaneous concurrency - for example the limit_conn_module of NGINX is used to limit the instantaneously concurrent connections. 
* Limit the average access rate of a time window - for example the RateLimiter of Guava and the limit_req module of NGINX can both be used to limit the average access rate per second. 
* Limit the remote API invocation rate 
* Limit the MQ consumption rate

## Throttling Algorithms 
There are two commonly used algorithms for throttling 
* The leaky bucket algorithm
* Token bucket algorithm

## Guava RateLimiter 
Guava is a open-source Java project that contains collection, caching, concurrency libraries, common annotations, string processing, I/O and so forth. 

Guava RateLimiter provides the token bucket algorithm implementation.
