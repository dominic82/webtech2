package de.webtech2.pages;

import de.webtech2.entities.User;

import de.webtech2.dao.UserDAO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public Link getImageLink(Long userId, boolean small) {
        return pageLink.createPageRenderLinkWithContext(ViewUserImage.class, new Object[] { userId, small });
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

    private StreamResponse getAvatar(boolean small) {
        return this.getStreamResponse(new ByteArrayInputStream(this.user.getImageAvatar()));
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
}
