package de.webtech2.pages;

import de.webtech2.entities.User;

import de.webtech2.dao.UserDAO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.PageRenderLinkSource;
import org.apache.tapestry5.services.Response;

public class ViewUserImage {

    @Inject
    private PageRenderLinkSource pageLink;
    @Inject
    @Path("context:layout/images/sample_noavatar_big.jpg")
    private Asset bigSampleAvatar;
    @Inject
    @Path("context:layout/images/sample_noavatar_small.jpg")
    private Asset smallSampleAvatar;
    @Inject
    private UserDAO userDAO;
    private User user;
    
    Dimension smallDimension = new Dimension(60, 60);
    Dimension bigDimension = new Dimension(120, 120);

    public Link getImageLink(Long userId, boolean small) {
        return pageLink.createPageRenderLinkWithContext(ViewUserImage.class, new Object[]{userId, small});
    }

    public StreamResponse onActivate(Long userId, boolean small) throws IOException {
        this.user = userDAO.getById(userId);

        if (user.getImageAvatar().length == 0) {
            return this.getSampleAvatar(small);
        } else {
            return this.getAvatar(small);
        }
    }

    private StreamResponse getSampleAvatar(boolean small) throws IOException {
        if (small) {
            return this.getStreamResponse(this.smallSampleAvatar.getResource().openStream());
        } else {
            return this.getStreamResponse(this.bigSampleAvatar.getResource().openStream());
        }
    }

    private StreamResponse getAvatar(boolean small) throws IOException {
        InputStream is = new ByteArrayInputStream(this.user.getImageAvatar());
        if (small) {
            return this.getStreamResponse(this.scale(is, this.smallDimension));
        } else {
            return this.getStreamResponse(this.scale(is,  this.bigDimension));
        }
    }

    private StreamResponse getStreamResponse(final InputStream is) {
        return new StreamResponse() {
            @Override
            public InputStream getStream() throws IOException {
                return is;
            }

            public String getContentType() {
                return "image/png";
            }

            @Override
            public void prepareResponse(Response response) {
            }
        };
    }

    private InputStream scale(InputStream is, Dimension d) throws IOException {
        BufferedImage imBuff = ImageIO.read(is);

        BufferedImage imScaled = this.doScale(imBuff, d);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imScaled, "png", baos);
        return new ByteArrayInputStream(baos.toByteArray());
    }

    private BufferedImage doScale(BufferedImage img, Dimension d) {
        float factor = getFactor(img.getWidth(), img.getHeight(), d);

        // create the image
        int w = (int) (img.getWidth() * factor);
        int h = (int) (img.getHeight() * factor);
        BufferedImage scaled = new BufferedImage(w, h,
                BufferedImage.TYPE_INT_RGB);

        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    float getFactor(int width, int height, Dimension dim) {
        float sx = dim.width / (float) width;
        float sy = dim.height / (float) height;
        return Math.min(sx, sy);
    }
}
