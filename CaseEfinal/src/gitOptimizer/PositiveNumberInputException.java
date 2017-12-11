/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gitOptimizer;

/**
 *
 * @author Viktor
 */
@SuppressWarnings("serial")
public class PositiveNumberInputException extends InputException {

    public PositiveNumberInputException(String variableName) {
        super(variableName, "must be a positive real number");
    }
}
