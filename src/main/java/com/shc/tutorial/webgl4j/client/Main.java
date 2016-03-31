package com.shc.tutorial.webgl4j.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.StyleElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.shc.webgl4j.client.WebGL10;
import com.shc.webgl4j.client.WebGLContext;

import static com.shc.webgl4j.client.WebGL10.*;

public class Main implements EntryPoint
{
    @Override
    public void onModuleLoad()
    {
        // The first thing one should do is to create a canvas. It is required to perform WebGL rendering.
        final Canvas canvas = Canvas.createIfSupported();

        if (canvas == null)
        {
            // Now if a canvas is null, that means that the browser has no HTML5 support.
            Window.alert("Error, cannot create canvas, please use a supported HTML5 browser.");
            return;
        }

        if (!WebGL10.isSupported())
        {
            // You can check if WebGL is supported. If not, report to the user that the browser is not supported.
            Window.alert("Error, your browser does not support WebGL, please use a supported browser.");
            return;
        }

        // Now set the dimensions of the canvas, and add it to the RootPanel.
        canvas.setCoordinateSpaceWidth(640);
        canvas.setCoordinateSpaceHeight(480);
        RootPanel.get().add(canvas);

        // Create the context, here we are creating a WebGL 1.0 context.
        WebGL10.createContext(canvas);

        // Set the clear color of the canvas, and clear the color buffer.
        glClearColor(0.16f, 0.5f, 0.72f, 1);
        glClear(GL_COLOR_BUFFER_BIT);

        // Create a Button to switch to fullscreen state
        Button fullScreen = new Button("Go Fullscreen");
        fullScreen.addClickHandler(new ClickHandler()
        {
            @Override
            public void onClick(ClickEvent event)
            {
                // Set to fullscreen mode.
                WebGLContext.getCurrent().requestFullscreen();

                // Reset the viewport to entire screen (prevent stretching)
                glViewport(0, 0, canvas.getElement().getClientWidth(), canvas.getElement().getClientHeight());
            }
        });

        RootPanel.get().add(fullScreen);

        // Add some CSS to the document to make true full screen in the browsers.
        StyleElement style = Document.get().createStyleElement();

        style.setInnerHTML(
                // Style as per the specification
                "canvas:fullscreen                          \n" +
                "{                                          \n" +
                "    position: absolute;                    \n" +
                "    top: 0; left: 0; right: 0; bottom: 0;  \n" +
                "    margin: auto;                          \n" +
                "    width: 100%   !important;              \n" +
                "    height: 100%  !important;              \n" +
                "}" +

                // Style for webkit browsers (Chrome & Safari)
                "canvas:-webkit-full-screen                 \n" +
                "{                                          \n" +
                "    position: absolute;                    \n" +
                "    top: 0; left: 0; right: 0; bottom: 0;  \n" +
                "    margin: auto;                          \n" +
                "    width: 100%   !important;              \n" +
                "    height: 100%  !important;              \n" +
                "}" +

                // Style for mozilla (Firefox)
                "canvas:-moz-full-screen                    \n" +
                "{                                          \n" +
                "    position: absolute;                    \n" +
                "    top: 0; left: 0; right: 0; bottom: 0;  \n" +
                "    margin: auto;                          \n" +
                "    width: 100%   !important;              \n" +
                "    height: 100%  !important;              \n" +
                "}" +

                // Style for MS browsers (IE and Edge)
                "canvas:-ms-fullscreen                      \n" +
                "{                                          \n" +
                "    position: absolute;                    \n" +
                "    top: 0; left: 0; right: 0; bottom: 0;  \n" +
                "    margin: auto;                          \n" +
                "    width: 100%   !important;              \n" +
                "    height: 100%  !important;              \n" +
                "}"
        );

        // Insert the CSS into head of the page
        Document.get().getHead().appendChild(style);

        setupKeyEvents(canvas);
        setupMouseEvents(canvas);
    }

    private void setupKeyEvents(Canvas canvas)
    {
        final Label keyPressLabel = new Label("No key pressed");
        final Label keyDownLabel = new Label("No key down");
        final Label keyUpLabel = new Label("No key up");

        canvas.addKeyDownHandler(new KeyDownHandler()
        {
            @Override
            public void onKeyDown(KeyDownEvent event)
            {
                keyDownLabel.setText("Key down: " + event.getNativeKeyCode());
            }
        });

        canvas.addKeyUpHandler(new KeyUpHandler()
        {
            @Override
            public void onKeyUp(KeyUpEvent event)
            {
                keyUpLabel.setText("Key up: " + event.getNativeKeyCode());
            }
        });

        canvas.addKeyPressHandler(new KeyPressHandler()
        {
            @Override
            public void onKeyPress(KeyPressEvent event)
            {
                keyPressLabel.setText("Key press: " + event.getCharCode());
            }
        });

        RootPanel.get().add(keyPressLabel);
        RootPanel.get().add(keyDownLabel);
        RootPanel.get().add(keyUpLabel);
    }

    private void setupMouseEvents(Canvas canvas)
    {
        final Label mouseDownLabel = new Label("Mouse down: none");
        final Label mouseUpLabel = new Label("Mouse up: none");
        final Label mouseMoveLabel = new Label("Mouse x: 0, y: 0");
        final Label mouseWheelLabel = new Label("Delta Scroll: 0");

        canvas.addMouseDownHandler(new MouseDownHandler()
        {
            @Override
            public void onMouseDown(MouseDownEvent event)
            {
                mouseDownLabel.setText("Mouse down: " + event.getNativeButton() +
                                       " at (" + event.getClientX() + ", " + event.getClientY() + ")");
            }
        });

        canvas.addMouseUpHandler(new MouseUpHandler()
        {
            @Override
            public void onMouseUp(MouseUpEvent event)
            {
                mouseUpLabel.setText("Mouse up: " + event.getNativeButton() +
                                       " at (" + event.getClientX() + ", " + event.getClientY() + ")");
            }
        });

        canvas.addMouseMoveHandler(new MouseMoveHandler()
        {
            @Override
            public void onMouseMove(MouseMoveEvent event)
            {
                mouseMoveLabel.setText("Mouse move: (" + event.getClientX() + ", " + event.getClientY() + ")");
            }
        });

        canvas.addMouseWheelHandler(new MouseWheelHandler()
        {
            @Override
            public void onMouseWheel(MouseWheelEvent event)
            {
                mouseWheelLabel.setText("Delta Scroll: " + event.getDeltaY());
            }
        });

        RootPanel.get().add(mouseDownLabel);
        RootPanel.get().add(mouseUpLabel);
        RootPanel.get().add(mouseMoveLabel);
        RootPanel.get().add(mouseWheelLabel);
    }
}