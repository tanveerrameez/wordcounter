# wordcounter
Word Counter test coding

#Assumptions
1. Behaviour is assumed to be single threaded. If this is to be implemented for multi-threading,
there will be some changes like used of ConcurrentHashMap instead of HashMap, 
and instead of map.get()/map.put, ConcurrentHashMap.compute() will be used to increment the counter
in an atomic operation.
3. The Translator service only accepts the word and can deduce the language and find the 
corresponding English word. So no language code argument is provided.
4. Only alphabetic characters allowed as per the specifications

# Design
1. An interface WordCounter is implemented as WordCounterImpl
2. The persistence interface WordPersistence is implemented as WordPerisistenceMapImpl, 
which implements the persistence as a Map in memory. WordCounterImpl uses WordPersistence to save the words.
3. The rules governing the validity of the word (for example, only alphabetic characters allowed) are
provided by a WordRules class. The idea is that, any further changes can be provided in this class, by extending the class or 
using the accessor/mutator methods, thus separating the rules from the WordCounter.
4. The external Translator service is accessed as an interface Translator. So no implementation is made.
5. The junit classes WordCounterTest and WordPersistenceMapImplTest provide the test cases
6. The testing of the Translator interface is done using Mockito to mock the behaviour of this interface.

# How to run
1. Compile and run the tests:  mvn clean test
2. com.practice.wordcounter.WordCounterTest contains the test cases for WordCounter implementation
3. com.practice.wordcounter.WordPersistenceMapImplTest contains test cases for WordPersistence implementation
