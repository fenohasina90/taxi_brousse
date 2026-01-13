<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/WEB-INF/jsp/layout/header.jsp"%>

<body class="vertical-layout vertical-menu 2-columns fixed-navbar"
      data-open="click" data-menu="vertical-menu" data-col="2-columns">

<%@ include file="/WEB-INF/jsp/layout/sidebar.jsp"%>

<div class="app-content content">
    <div class="content-overlay"></div>
    <div class="content-wrapper">

        <div class="content-header row">
            <div class="content-header-left col-md-6 col-12 mb-2">
                <h3 class="content-header-title">Modifier tarif</h3>
            </div>
        </div>

        <div class="content-body">
            <section id="basic-form-layouts">
                <div class="row">
                    <div class="col-md-6 offset-md-3">

                        <div class="card">
                            <div class="card-header"></div>
                            <div class="card-content collpase show">
                                <div class="card-body">

                                    <form method="post" action="${pageContext.request.contextPath}/tarifs/${tarif.id}/modifier"
                                          class="form form-horizontal form-bordered">

                                        <div class="form-body">

                                            <div class="form-group row">
                                                <label class="col-md-4 label-control">Trajet</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">
                                                        ${tarif.trajet.gareDepart.ville} -> ${tarif.trajet.gareArrivee.ville}
                                                    </p>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-md-4 label-control">Type de voyage</label>
                                                <div class="col-md-8">
                                                    <p class="form-control-static">${tarif.typeVoyage.description}</p>
                                                </div>
                                            </div>

                                            <div class="form-group row">
                                                <label class="col-md-4 label-control">Montant (Ar)</label>
                                                <div class="col-md-8">
                                                    <input type="number" step="0.01" min="0" class="form-control"
                                                           name="montant" value="${tarif.montant}" required>
                                                </div>
                                            </div>

                                        </div>

                                        <div class="form-actions text-right">
                                            <a href="${pageContext.request.contextPath}/tarifs" class="btn btn-secondary mr-1">
                                                Annuler
                                            </a>
                                            <button type="submit" class="btn btn-primary">
                                                <i class="la la-check-square-o"></i> Enregistrer
                                            </button>
                                        </div>

                                    </form>

                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </div>
    </div>
</div>

<%@ include file="/WEB-INF/jsp/layout/footer.jsp"%>
</body>
</html>
