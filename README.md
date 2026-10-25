**CS 320: Software Test Automation and Quality Assurance
Andre Evans | Southern New Hampshire University**

This portfolio contains the contact service code and JUnit tests from Project One, along with my summary and reflections report from Project Two. These projects show how I used software requirements to develop Java classes, test their behavior, and evaluate software quality.
Portfolio Artifacts
File	Description
[Contact.java](Contact.java)	Stores contact information and validates field requirements.
[ContactService.java](ContactService.java)	Adds, retrieves, updates, and deletes contacts using unique IDs.
[ContactTest.java](ContactTest.java)	Tests contact creation, field limits, invalid inputs, and updates.
[ContactServiceTest.java](ContactServiceTest.java)	Tests service operations, duplicate IDs, and changes to the correct contact.
[Project Two Summary and Reflections](Andre_Evans_CS320_Project_Two_Summary_and_Reflections.docx)	Explains my testing approach, results, techniques, and lessons learned.

The Java tests use JUnit 5. To run them, add the four Java files to a Java project with JUnit 5 available and run ContactTest and ContactServiceTest as JUnit tests.

**How can I ensure that my code, program, or software is functional and secure?**
I can help ensure that my software is functional by checking it against the requirements and testing both successful and unsuccessful situations. In my contact service project, I used JUnit tests to check valid contacts, null values, field length limits, duplicate IDs, and invalid updates. I also checked that an operation did not accidentally change another contact. Running these tests after changes can help catch problems before they affect other parts of the program. For security, I need to validate input, protect sensitive information, and control access to data. In a complete application, I would also test authentication and permissions. Passing unit tests is useful evidence, but it does not guarantee that an entire application is secure.

**How do I interpret user needs and incorporate them into a program?**
I start by understanding what the user needs to accomplish and turning those needs into specific requirements. If something is unclear, I would ask questions and use examples to confirm the expected behavior before coding it. For this project, the requirements identified the contact information to store and the actions the service needed to support. I translated those requirements into validation rules, service methods, and test cases. For example, the phone number had to contain exactly 10 digits, so I tested valid numbers along with numbers that were too short, too long, or contained other characters. Connecting each requirement to a test helps me check that the program meets the user's needs.

**How do I approach designing software?**
I approach software design by reviewing the requirements, breaking the problem into smaller parts, and deciding what each part should be responsible for. In the contact service project, the Contact class handled contact data and validation, while ContactService managed the stored contacts and their operations. This separation made the code easier to understand and test. I prefer to build in small steps, check the results, and make improvements as I go. I also think about invalid input and possible errors during design instead of waiting until the program is finished. This course helped me see testing as part of development from the beginning, which supports software that is easier to maintain and update.
