Feature: QA Testing- Assessment - To Register,validate,delete patent

Scenario Outline: Verify register,validate registered details,calculate age,upload attachment and verify BMI and visit dates and delete patent
Given User loads the url
When User logs into the application
And Verify the sucessfull login
And user navigates to patient register page
And User Register a patient "<FirstName>" "<LastName>" "<Gender>" "<Day>" "<Month>" "<Year>" "<Address1>" "<Address2>" "<Village>" "<State>" "<Country>" "<Postal>" "<Mobile>"
Then Verify Registered patient details "<FirstName>" "<LastName>" "<Gender>" "<Day>" "<Month>" "<Year>" "<Address1>" "<Address2>" "<Village>" "<State>" "<Country>" "<Postal>" "<Mobile>"
Then Verify the age is calculated correctly based on the date of birth provided "<Day>" "<Month>" "<Year>"
Then User upload the image and verify the attachment
When User navigates to profile page
And User verifies attachment and end visit
Then User start visit and verified updated the bmi "<Height>" "<Weight>"
When User navigates to profile page
When User navigates to profile page
Then User end visit and verified updated the bmi in personal page
Then User verifies recent visit dates and attachment upload tag
When User Click on Add Past Visit and verify the future date is not clickable in the date picker
Then User delete patient and verify the deleted user not listed in search
Then I quit the browser
Examples:
|FirstName|LastName|Gender|Day|Month|Year|Address1|Address2|Village|State|Country|Postal|Mobile|Height|Weight|
|Test|Colab|Male|10|February|2000|Test|Main Street|Newyork|NY|USA|10022|8877665544|172|72|