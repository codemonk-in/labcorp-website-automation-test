Feature: LabCorp Careers Page

  Scenario: User searches for a position and validates job details
    Given the user is on the LabCorp home page
    When the user navigates to the Careers page
    And the user searches for "Method Developer"
    And the user clicks on the first job result
    Then the job title should match the result listing
    And the job location should match the result listing
    And the job ID should match the result listing
    Then the job description third paragraph first sentence should be:
    """
    Are you looking for a varied role that will offer you the opportunity to work on a variety of studies using different instruments within an analytical chemistry environment?
    """
    And the job description second bullet under "Main responsibilities include:" should be:
    """
    To provide advice and guidance on all aspects of methodology relevant to the department
    """
    And the job description second bullet under "Skills and experience:" should contain "GC-MS"
    And the user clicks on Apply Now button
    Then the user is redirected back to the Careers page
