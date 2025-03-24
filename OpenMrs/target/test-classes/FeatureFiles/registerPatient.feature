
Feature: User register and delete a patient in OpenMRS

 @Smoke
  Scenario: User Successfully Register, delete, and verify the patient is deleted
    Given User open the browser and hit the site url
    And User enters the Admin username and password, selects the location, and clicks on the login button
    Then User verify the dashboard page is redirected
    And User register a patient using detail of Demographics
    Then User verify the demographic details at confirm page
    Then User clicks on confirm and verify the patient details page is redirected
    And User clicks on start visit and clicks on confirm visit
    And User uploads the file and verify the toaster message 
    Then User redirecting to Patient details screen and verify the attachment
    Then User verify the Recent Visit has one entry for the current date with the Attachment Upload
    And User clicks on end visit and delete the patient by providing the reason
    Then User clicks on confirm button and verify the toaster message
    Then User verify the deleted patient is not showing by using the search box 
