/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Map;

/**
 *
 * @author agarcia.gonzalez
 */
public interface CallbackRespuesta {
    
    public void success (Map<String,String> contenido);    // El código está entre 200 y 299
    public void error (Map<String,String> contenido);      // El código está entre 400 y 999
    
}
