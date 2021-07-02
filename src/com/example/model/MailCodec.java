package com.example.model;

import org.bson.*;
import org.bson.codecs.*;
import org.bson.types.ObjectId;
import com.mongodb.MongoClient;
import java.util.Date;
import java.util.*;
import java.time.*;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.codecs.configuration.CodecRegistry;

/**
 * Mongo Decoder for Mail.
 */
public class MailCodec implements CollectibleCodec<Mail> {

    private final CodecRegistry registry;
    private final Codec<Document> documentCodec;
    private final MailConverter converter;

    public MailCodec() {
        this.registry = MongoClient.getDefaultCodecRegistry();
        this.documentCodec = this.registry.get(Document.class);
        this.converter = new MailConverter();
    }

 
    public MailCodec(Codec<Document> codec) {
        this.documentCodec = codec;
        this.registry = MongoClient.getDefaultCodecRegistry();
        this.converter = new MailConverter();
    }

    public MailCodec(CodecRegistry registry) {
        this.registry = registry;
        this.documentCodec = this.registry.get(Document.class);
        this.converter = new MailConverter();
    }

    @Override
    public void encode(
                    BsonWriter writer,
                    Mail m,
                    EncoderContext encoderContext
                ) {
        Document document = this.converter.convert(m);

        documentCodec.encode(writer, document, encoderContext);
    }

    /**
     * Get the class that this Codec works with.
     * @return Returns the class that this Codec works with.
     */
    @Override
    public Class<Mail> getEncoderClass() {
        return Mail.class;
    }

    @Override
    public Mail decode(BsonReader reader, DecoderContext decoderContext) {
        Document document = documentCodec.decode(reader, decoderContext);
        Mail m = this.converter.convert(document);

        return m;
    }

    @Override
    public Mail generateIdIfAbsentFromDocument(Mail m) {
        if (!documentHasId(m)) {
            m.setMailId(new ObjectId().toHexString());
        }

        return m;
    }

    @Override
    public boolean documentHasId(Mail m) {
        return (m.getMailId() != null);
    }

    @Override
    public BsonString getDocumentId(Mail m)
    {
        if (!documentHasId(m)) {
            throw new IllegalStateException("The document does not contain an _id");
        }

        return new BsonString(new ObjectId().toHexString());
    }

}
