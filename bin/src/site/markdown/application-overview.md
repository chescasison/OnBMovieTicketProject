# Application Overview

## Background

Orange and Bronze is diversifying into the commercial/retail real estate business. As such, it plans to operate movie theaters, and needs a software-based system in selling tickets.

## Initial Analysis

This section provides the initial analysis of requirements documented as user stories (with brief notes and examples).

These movie theaters have several cinemas. Each cinema has its own seat layout. When a customer walks up to buy tickets, a customer service representative asks for the desired movie and time. Given the desired movie and time, the customer chooses the seat(s). Service representative selects the seat(s) in behalf of the customer. Service representative then verifies the chosen seats and show time. After verification, service representative asks for payment and prints the tickets.

The following stories have been split and sorted based on business value, risk, size, and cost. You *must* complete one story first, before moving to the next one. To complete a story, you *must* tag it in the code repository, have an assessor check it.

1. As a user, I want to see the movie screening schedules.
	- As a service representative, I want to see the movie screening schedules, so I can inform the customer and ask for his/her choice.
	- As a user, I want to browse the movie screening schedules, so I can have an idea of show times before I arrive at the movie theater. Notes follow:
		- Movie posters are a *must* to entice people to come and watch.
		- The user need not be authenticated.
2. As a service representative, I want to display the available seats of a given movie screening schedule, so I can let the customer choose seat(s). Notes follow:
	- Seats must be displayed in a way that shows its position relative to the screen, so that customers can choose properly.
	- Seats that have already been taken/bought should be indicated as such.
	- Seat numbers should be easy for the customer to choose (and for ushers too). Like A3, I7, or C8, and **not** like 123xyz-fAzbK.
	- The service representative must be properly authenticated. See constraint below.
3. As a service representative, I want to book selected seats and print tickets. (Note: Other service representatives are likely to be booking for the same movie seats at the same time, on behalf of the customer)
	- Note to developer: There *must be* an automated integration test that simulates two or more users booking/buying overlapping seats. It should prove that system only allows one seat/screening to be sold to one customer.
4. As a system administrator, I want to manage the movie screening schedule. Notes follow:
	- Information about the movie needs to be captured (including title, summary, director, actor(s), running time). This includes *uploading* the movie poster image(s) (and possibly other related content).
5. As a system administrator, I want to set-up the seat layout of each cinema.
6. (Constraint) The user must be properly authenticated and authorized before accessing the restricted parts of the system. Only system administrators can manage movie schedules and cinemas. Only service representatives can buy movie seats and print tickets.
