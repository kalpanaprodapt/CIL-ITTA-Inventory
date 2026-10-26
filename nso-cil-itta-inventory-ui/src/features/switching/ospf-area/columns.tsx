import { Link as RouterLink } from 'react-router-dom';
import { BooleanCell, ShortId, StatusBadge } from '@/shared/components/cells';
import type { Column } from '@/shared/components/DataTable';
import { formatDate, formatDateTime, formatDecimal, formatNumber, truncate } from '@/shared/utils/format';
import { OSPF_AREA_AREA_TYPE_LABELS, OSPF_AREA_CRITICALITY_LABELS } from './types';
import type { OspfArea } from './types';

export const ospfAreaColumns: Column<OspfArea>[] = [
  {
    key: 'code',
    header: 'Code',
    sortable: true,
    align: 'left',
    render: (row) => <RouterLink to={`/switching/ospf-areas/${row.id}`}>{row.code}</RouterLink>,
  },
  {
    key: 'name',
    header: 'Name',
    sortable: true,
    align: 'left',
    render: (row) => row.name ?? '—',
  },
  {
    key: 'description',
    header: 'Description',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.description, 60),
  },
  {
    key: 'status',
    header: 'Status',
    sortable: true,
    align: 'left',
    render: (row) => <StatusBadge status={row.status} />,
  },
  {
    key: 'externalRef',
    header: 'External Ref',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.externalRef ?? '—',
  },
  {
    key: 'areaId',
    header: 'Area Id',
    sortable: true,
    align: 'left',
    render: (row) => row.areaId ?? '—',
  },
  {
    key: 'areaType',
    header: 'Area Type',
    sortable: true,
    align: 'left',
    render: (row) => row.areaType ? OSPF_AREA_AREA_TYPE_LABELS[row.areaType] : '—',
  },
  {
    key: 'deviceCount',
    header: 'Device Count',
    sortable: true,
    align: 'right',
    render: (row) => formatNumber(row.deviceCount),
  },
  {
    key: 'authType',
    header: 'Auth Type',
    sortable: true,
    align: 'left',
    render: (row) => row.authType ?? '—',
  },
  {
    key: 'criticality',
    header: 'Criticality',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.criticality ? OSPF_AREA_CRITICALITY_LABELS[row.criticality] : '—',
  },
  {
    key: 'costCenter',
    header: 'Cost Center',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => row.costCenter ?? '—',
  },
  {
    key: 'notes',
    header: 'Notes',
    sortable: false,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => truncate(row.notes, 60),
  },
  {
    key: 'effectiveFrom',
    header: 'Effective From',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveFrom),
  },
  {
    key: 'effectiveTo',
    header: 'Effective To',
    sortable: true,
    align: 'left',
    hiddenByDefault: true,
    render: (row) => formatDate(row.effectiveTo),
  },
];

export const ospfAreaDefaultVisible = ospfAreaColumns.filter((c) => !c.hiddenByDefault).map((c) => c.key);
