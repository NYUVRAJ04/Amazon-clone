package org.happiest.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasssword {
    private String password;
    private String repeatPassword;
    private String email; // Add email field if necessary
}
