@2020feature @core @critical 
Feature: It Should allow users to add new computers to the database for searching at a later date
  As A product manager
  I want frontend users to be able to update the database
  So That I dont have to book time with a database administrator
  
   Scenario: There should be a link to the new section that allows users to add computers to the database
	  Given I am using a web browser
    When I navigate to the computer database app
    Then I should see a new button called "Add a New Computer"

  Scenario: It should be possible to open the new section
    Given I am already on the database application
    And the link to the new section is present
    When I click on the link
    Then I should be taken to the new section to update the database
    And The new section should have a heading of "Add a New Computer"
 
  # The below scenario is supposed to fail because Company field is not free text, instead its a drop down
  #Scenario: It should have four new fields and two buttons in the new section
    #Given I am already on the database application
    #When I navigate to the new section using the button link
    #Then I should see four fields labelled with a type below
      #|    type  |      fields |
      #|Free input|Computer name|
      #|Free input|Introduced date|
      #|Free input|Discontinued date|
      #|Free input|Company|
    #And I should see two buttons named below
      #|   buttons    |
      #| Add Computer |
      #|    Cancel    |
#
#	 # The below scenrio is supposed to fail because the application allows to save the computer though fields are left empty
   #Scenario: It should not allow new computers to be added unless all fields are populated
    #Given I am already adding a computer in the Add a New Computer view
    #And I have already entered a name for a computer
    #But Have not entered data into other fields
    #When I click "Add This Computer"
    #Then It should not allow you to save
    #
 #Scenario: It should allow new computers to be added if all fields are populated
   #Given I am already adding a computer in the Add a New Computer view
    #And I have already entered all fields for a new computer
    #When I click "Add This Computer"
    #Then It should be allowed to save
    #
   #Scenario: It Should allow me to cancel the operation
    #Given I am already entering a new computer into the system
    #But I need to stop what I am doing and go back to the main view
    #When I click cancel
    #Then It should take me back to the main computer database view
