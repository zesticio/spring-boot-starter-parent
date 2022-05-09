# TODO
 JMSReplyTo —Used to specify a destination where a reply should be sent. This
header is commonly used for request/reply style of messaging. Messages sent
with this header populated typically expect a response, but it’s actually optional.
The client must make the decision to respond or not.
 JMSType—Used to semantically identify the message type. This header is used by
few vendors and has nothing to do with the payload Java type of the message.

JMSRedelivered—Used to indicate the liklihood that a message was previously
delivered but not acknowledged. This can happen if a consumer fails to
acknowledge delivery, or if the JMS provider hasn’t been notified of delivery
such as an exception being thrown that prevents the acknowledgement from
reaching the provider.

Message selectors
There are times when a JMS client is subscribed to a given destination, but it may want
to filter the types of messages it receives. This is exactly where headers and properties
can be used. For example, if a consumer registered to receive messages from a queue
is only interested in messages about a particular stock symbol, this is easy as long as
each message contains a property that identifies the stock symbol of interest. The JMS
client can utilize JMS message selectors to tell the JMS provider that it only wants to
receive messages containing a particular value in a particular property.

Publish / Subscribe Domain
The publish/subscribe (pub/sub) messaging domain uses destinations known as
topics. Publishers send messages to the topic and subscribers register to receive messages
from the topic. Any messages sent to the topic are automatically delivered to all
subscribers. This messaging domain is similar to subscribing to a mailing list where all
subscribers will receive all messages sent to the mailing list in a one-to-many paradigm.