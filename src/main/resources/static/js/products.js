$(document).ready(function () {
	moment.locale('pt-BR');
    var table = $('#table-products').DataTable({
    	searching: true,
    	order: [[ 1, "asc" ]],
    	lengthMenu: [5, 10],
        processing: true,
        serverSide: true,
        responsive: true,
        ajax: {
            url: '/admin/products/datatables/products',
            data: 'data'
        },
        columns: [
            {data: 'id'},
            {data: 'title'},
            {data: 'category.name'},
            {data: 'price'},
            {orderable: false,
             data: 'id',
                "render": function(id) {
                    return `<a class="btn btn-success btn-sm btn-block" href="/products/edit/${id}"
                        role="button"><i class="fas fa-edit"></i></a>`;
                }
            },
            {orderable: false,
             data: 'id',
                "render": function(id) {
                    return `<a class="btn btn-danger btn-sm btn-block" href="/products/remove/${id}"
                    	role="button" data-toggle="modal" data-target="#confirm-modal">
                    	<i class="fas fa-times-circle"></i></a>`;
                }
            }
        ]
    });
});