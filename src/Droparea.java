import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eczn on 2016/12/4.
 */
public class Droparea extends Container implements DropTargetListener {
    Jplayer jp;

    public Droparea(Jplayer Jplayer ){
        jp = Jplayer;
        new DropTarget(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        System.out.println("dropActionChanged");
    }
    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        System.out.println("dragEnter");
    }
    @Override
    public void dragExit(DropTargetEvent dte) {
        System.out.println("dragExit");
    }
    @Override
    public void dragOver(DropTargetDragEvent dtde) {
//        System.out.println("dragOver");
    }
    @Override
    public void drop(DropTargetDropEvent dtde) {
        System.out.println("drop");
        try {
//            Transferable tr = dtde.getTransferable();

            if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

                List list = (List) (
                        dtde.getTransferable()
                                .getTransferData(DataFlavor.javaFileListFlavor)
                );
                Iterator iterator = list.iterator();
                while (iterator.hasNext()) {
                    File f = (File) iterator.next();
                    System.out.println("拖入的文件是："+f.getAbsolutePath());
                }
                dtde.dropComplete(true);
                //this.updateUI();
            } else {
                dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
                Object drop2here = dtde.getTransferable().getTransferData(DataFlavor.stringFlavor);

                jp.livelist.addElem(drop2here.toString());

                System.out.println(dtde.getTransferable().getTransferData(DataFlavor.stringFlavor));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        }
    }
}
