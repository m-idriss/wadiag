package com.dime.wadiag.diag.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.dime.wadiag.diag.controller.TermController;

@Component
public class TermModelAssembler implements RepresentationModelAssembler<Term, EntityModel<Term>> {

    @Override
    public @NonNull EntityModel<Term> toModel(@NonNull Term entity) {
        EntityModel<Term> termModel = EntityModel.of(entity);

        termModel.add(linkTo(methodOn(TermController.class).getOne(entity.getId())).withSelfRel());
        termModel.add(linkTo(methodOn(TermController.class).listAll()).withRel(IanaLinkRelations.COLLECTION));
        return termModel;
    }
}
