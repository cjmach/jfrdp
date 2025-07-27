/*
 * Licensed to the Apache Software Foundation (ASF) under one or more 
 * contributor license agreements.  See the NOTICE file distributed with this 
 * work for additional information regarding copyright ownership. The ASF 
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 * 
 *   https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT 
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the 
 * License for the specific language governing permissions and limitations
 * under the License.  
 */
package pt.cjmach.jfrdp.ui;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import pt.cjmach.jfrdp.lib.RdpInput;

/**
 *
 * @author cmachado
 */
class RdpDisplayEventListener implements ComponentListener, FocusListener, KeyListener,
        MouseListener, MouseMotionListener, MouseWheelListener {

    private final RdpDisplay display;

    RdpDisplayEventListener(RdpDisplay display) {
        this.display = display;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        display.onParentComponentResized(e);
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }

    @Override
    public void focusGained(FocusEvent e) {
        display.onEnter();
    }

    @Override
    public void focusLost(FocusEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
        display.onKeyTyped(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        display.onKeyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        display.onKeyReleased(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        display.onMouseButtonEvent(e, true);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        display.onMouseButtonEvent(e, false);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        display.onMouseEvent(RdpInput.MOUSE_MOVE, e.getX(), e.getY());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        display.onMouseScroll(e);
    }
}
