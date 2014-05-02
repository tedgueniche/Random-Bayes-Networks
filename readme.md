RandBayezNets
=============

The project present an implementation of random bayesian networks. The provided data come from the Myers-Briggs Type Indicator (MBTI) personality test.


Application
===========

The test application consist in minimizing the number of questions ask to an user. There is over 100 questions in the MBTI test and with the random bayes networks we are able to cut the number of question by up to 70% with a confidence of over 90% and a coverage of over 90%.


How it works
==============

Given enough training data, the random bayes network are able to model the training data to accuratly predict the answer to a question given the answer the user provided for a subset of the questions. Using this logic, we are able to pick the minimum set of questions that need to be answered to predict the output of the test.


