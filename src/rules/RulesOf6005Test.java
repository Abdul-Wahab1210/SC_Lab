/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package rules;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * JUnit tests for RulesOf6005.
 */
public class RulesOf6005Test {
    
    /**
     * Tests the mayUseCodeInAssignment method.
     */
    @Test
    public void testMayUseCodeInAssignment() {
        assertFalse("Expected false: un-cited publicly-available code",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, false, false));
        assertTrue("Expected true: self-written required code",
                RulesOf6005.mayUseCodeInAssignment(true, false, true, true, true));
        
        // Case 3: Cited but not available to others
        assertFalse("Expected false: Cited code unavailable to others",
                RulesOf6005.mayUseCodeInAssignment(false, false, false, true, false));
        
        // Case 4: Code not self-written, but cited and available to others, no implementation requirement
        assertTrue("Expected true: cited code available to others, no implementation required",
                RulesOf6005.mayUseCodeInAssignment(false, true, false, true, false));
        
        // Case 5: Self-written, implementation not required
        assertTrue("Expected true: self-written code, no implementation requirement",
                RulesOf6005.mayUseCodeInAssignment(true, false, false, false, false));
    }
}
