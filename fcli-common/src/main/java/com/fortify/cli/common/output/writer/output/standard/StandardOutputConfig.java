/*******************************************************************************
 * (c) Copyright 2020 Micro Focus or one of its affiliates
 *
 * Permission is hereby granted, free of charge, to any person obtaining a 
 * copy of this software and associated documentation files (the 
 * "Software"), to deal in the Software without restriction, including without 
 * limitation the rights to use, copy, modify, merge, publish, distribute, 
 * sublicense, and/or sell copies of the Software, and to permit persons to 
 * whom the Software is furnished to do so, subject to the following 
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be included 
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY 
 * KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE 
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS 
 * IN THE SOFTWARE.
 ******************************************************************************/
package com.fortify.cli.common.output.writer.output.standard;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fortify.cli.common.output.OutputFormat;
import com.fortify.cli.common.output.cli.mixin.UnirestOutputHelperMixins;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * TODO Move this class to the writer.output package once all commands have been refactored to use {@link UnirestOutputHelperMixins}.
 * @author rsenden
 *
 */
@Accessors(fluent = true)
// TODO Add null checks in case any input or record transformation returns null?
public class StandardOutputConfig {
    @Getter @Setter private OutputFormat defaultFormat;
    private final List<BiFunction<OutputFormat,JsonNode,JsonNode>> inputTransformers = new ArrayList<>();
    private final List<BiFunction<OutputFormat,JsonNode,JsonNode>> recordTransformers = new ArrayList<>();
    
    public final StandardOutputConfig inputTransformer(final Function<OutputFormat, Boolean> applyIf, final UnaryOperator<JsonNode> transformer) {
        if ( transformer!=null ) {
            inputTransformers.add((fmt,o)->!applyIf.apply(fmt) ? o : transformer.apply(o));
        }
        return this;
    }
    
    public final StandardOutputConfig inputTransformer(UnaryOperator<JsonNode> transformer) {
        return inputTransformer(fmt->true, transformer);
    }
    
    public final StandardOutputConfig recordTransformer(Function<OutputFormat, Boolean> applyIf, UnaryOperator<JsonNode> transformer) {
        if ( transformer!=null ) {
            recordTransformers.add((fmt,o)->!applyIf.apply(fmt) ? o : transformer.apply(o));
        }
        return this;
    }
    
    public final StandardOutputConfig recordTransformer(UnaryOperator<JsonNode> transformer) {
        return recordTransformer(fmt->true, transformer);
    }
    
    final JsonNode applyInputTransformations(OutputFormat outputFormat, JsonNode input) {
        return applyTransformations(inputTransformers, outputFormat, input);
    }
    
    final JsonNode applyRecordTransformations(OutputFormat outputFormat, JsonNode record) {
        return applyTransformations(recordTransformers, outputFormat, record);
    }
    
    private final JsonNode applyTransformations(List<BiFunction<OutputFormat, JsonNode, JsonNode>> transformations, OutputFormat outputFormat, JsonNode input) {
        return transformations.stream()
                .reduce(input, (o, t) -> t.apply(outputFormat, o), (m1, m2) -> m2);
    }
    
    public static final StandardOutputConfig csv() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.csv);
    }
    
    public static final StandardOutputConfig json() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.json);
    }
    
    public static final StandardOutputConfig table() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.table);
    }
    
    public static final StandardOutputConfig tree() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.tree);
    }
    
    public static final StandardOutputConfig xml() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.xml);
    }
    
    public static final StandardOutputConfig yaml() {
        return new StandardOutputConfig().defaultFormat(OutputFormat.yaml);
    }

    public static final StandardOutputConfig details() {
        // TODO For now we use yaml output, until #104 has been fixed so we can properly use tree() instead
        return yaml();
    }
}
