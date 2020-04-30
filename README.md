# wordcounter
Word Counter test coding

#Assumptions
1. Only alphabetic characters allowed
2. The Translator service only accepts the word and can deduce the language and find the 
corresponding English word. 

# Design
1. An interface WordCounter is implemented as WordCounterImpl
2. The persistence interface WordPersistence is implemented as WordPerisistenceMapImpl, 
which implements the persistence as a Map in memory.
3. The rules governing the validity of the word (for example, only alphabetic characters allowed) are
provided by a WordRules class. The idea is that, any further changes can be provided in this class, by extending the class or 
using the accessor/mutator methods.
4. The external Translator service is accessed as an interface Translator. So no implementation is made.
The testing is done using Mockito to mock the behaviour of this interface.

# How to run