package util;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Utility class for managing dialogs to ensure they are opened in the
 * Event Dispatch Thread.
 */
public class DialogUtils {

    public static String setupNullMessage =  "<html>" +
            "<p>Could not initialize world. Either specify a valid world setup file in KaraWorld or instantiate <br>" +
            "a subclass of KaraWorld (right-click on world, e.g. GameScreen, and choose new). </p><br><p><i>" +
            "Konnte keine Welt laden. Entweder muss eine gueltige world setup Datei in KaraWorld definiert werden oder eine <br>" +
            "Subklasse von KaraWelt muss instanziiert werden (Rechtsklick auf die Welt, z.B. GameScreen, und new auswaehlen).</i></p>" +
            "</html>";

    /**
     * Calls {@link JOptionPane#showMessageDialog(Component, Object, String, int)} and ensures
     * it is called on the Event Dispatch Thread.
     */
    public static void showMessageDialogEdt(final Component parentComponent,
                                            final Object message, final String title, final int messageType) {

        Runnable task = new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(parentComponent, message, title,
                        messageType);
            }
        };

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls {@link JOptionPane#showInputDialog(Object)} and ensures it is called on
     * the Event Dispatch Thread.
     */
    public static String showInputDialogEdt(final Object message) {
        String result = "";

        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return JOptionPane.showInputDialog(message);
            }
        });

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Calls  and ensures it is called on
     * the Event Dispatch Thread.
     */
    public static Object showInputDialogEdt(final Component parentComponent,
                                            final Object message,
                                            final String title, final int messageType, final Icon icon,
                                            final Object[] selectionValues, final Object initialSelectionValue) {
        Object result = "";

        FutureTask<Object> task = new FutureTask<Object>(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return JOptionPane.showInputDialog(parentComponent, message, title, messageType, icon, selectionValues, initialSelectionValue);
            }
        });

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Calls {@link JOptionPane#showConfirmDialog(Component, Object, String, int)}
     * and ensures it is called on the Event Dispatch Thread.
     */
    public static int showConfirmDialogEdt(final Component parentComponent,
                                           final Object message, final String title, final int optionType) {
        Integer result = 0;

        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return JOptionPane.showConfirmDialog(parentComponent, message, title, optionType);
            }
        });

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }


    /**
     * Calls
     * and ensures it is called on the Event Dispatch Thread.
     */
    public static int showOptionDialogEdt(final Component parentComponent,
                                          final Object message,
                                          final String title, final int optionType, final int messageType,
                                          final Icon icon, final Object[] options, final Object initialValue) {
        Integer result = 0;

        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return JOptionPane.showOptionDialog(parentComponent, message, title, optionType, messageType, icon, options, initialValue);
            }
        });

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Calls {@link JFileChooser#showSaveDialog(Component)} and ensures it is called on
     * the Event Dispatch Thread.
     *
     * @param fileChooser the file chooser where <code>showSaveDialog</code> should be called on.
     * @param parent      the parent component of the dialog; can be <code>null</code>
     * @return the return state of the file chooser on popdown:
     * <ul>
     * <li>JFileChooser.CANCEL_OPTION
     * <li>JFileChooser.APPROVE_OPTION
     * <li>JFileChooser.ERROR_OPTION if an error occurs or the
     * dialog is dismissed
     * </ul>
     */
    public static int showSaveDialogEdt(final JFileChooser fileChooser, final Component parent) {
        int result = 0;

        FutureTask<Integer> task = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fileChooser.showSaveDialog(parent);
            }
        });

        try {
            if (EventQueue.isDispatchThread()) {
                task.run(); // Already on Event Dispatch Thread, so we can just run it.
            } else {
                EventQueue.invokeAndWait(task);
            }
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return result;
    }

}