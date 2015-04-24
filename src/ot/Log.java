/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ot;

import java.nio.ByteBuffer;

/**
 *
 * @author TIGER
 */
public interface Log {
    void write(ByteBuffer o);
    void info(Object o);
    void error(Object o);
}
