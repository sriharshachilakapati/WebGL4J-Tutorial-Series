package com.shc.tutorial.webgl4j.client;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.shc.webgl4j.client.WebGL10;

import static com.shc.webgl4j.client.WebGL10.*;

public class Main implements EntryPoint
{
    @Override
    public void onModuleLoad()
    {
        // The first thing one should do is to create a canvas. It is required to perform WebGL rendering.
        Canvas canvas = Canvas.createIfSupported();

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

        // Set the clear color of the canvas, and clear the color buffer. Here we make it back.
        glClearColor(0, 0, 0, 1);
        glClear(GL_COLOR_BUFFER_BIT);
    }
}