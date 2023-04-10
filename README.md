# Failure Handling

# Overview
This example provides usage of Resilience4j Retry and Circuitbreaker in Spring boot Services

There are three services which provide a Synchronous communication between these services as per given flow:

           Consumer(PostMan)
                |
                |
                |
                V
           PaymentService
                |
                |
                |
                V
           BankingService
               |
               |
               |
               V
           MambuService (External Partner Service)

 
