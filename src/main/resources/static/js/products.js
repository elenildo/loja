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
            {orderable: false,
            data: 'images',
                "render": function(images, type, data) {
                    if(images != null && images.length > 0 && type=='display')
                        data = `<img src="/image/${images[0]}" class="w-50"/>`;
                    else
                        data = `<img src="/img/prod-not-found.webp" class="w-50"/>`;

                    return data
                }
            },
            {data: 'id'},
            {data: 'title'},
            {data: 'category.name'},
            {data: 'price'},
            {orderable: false,
             data: 'id',
                "render": function(id, type, data) {
                    if(type=='display'){
                        data =  `<a class="btn btn-success btn-sm" href="/admin/products/update/${id}"
                                 role="button"><i class="fas fa-edit"></i></a>`;
                        return data;
                    }
                }
            },
            {orderable: false,
             data: 'id',
                "render": function(id) {
                    return `<a class="btn btn-danger btn-sm" href="/admin/products/remove/${id}"
                    	role="button" data-toggle="modal" data-target="#confirm-modal">
                    	<i class="fas fa-times-circle"></i></a>`;
                }
            }
        ]
    });
});