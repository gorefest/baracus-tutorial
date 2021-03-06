package org.baracustutorial.validation;

import org.baracus.validation.AbstractValidator;
import org.baracus.validation.ConstrainedView;
import org.baracustutorial.R;

/**
 * Created by marcus on 30.09.14.
 */
public class NameValidator extends AbstractValidator<String>{

    static final String pattern = "[A-Z]+[a-z]*";

    @Override
    public boolean validate(ConstrainedView<String> view) {
        return view.getCurrentValue().matches(pattern);
    }

    @Override
    public int getMessageId() {
        return R.string.nameViolation;
    }
}
