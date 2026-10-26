import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { VPN_SERVICE_VPN_TYPE_OPTIONS, VPN_SERVICE_TOPOLOGY_OPTIONS, VPN_SERVICE_CRITICALITY_OPTIONS } from './types';
import type { VpnServiceFilter } from './types';

interface Props {
  value: VpnServiceFilter;
  onChange: (next: VpnServiceFilter) => void;
  onReset: () => void;
}

export function VpnServiceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: VpnServiceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Vpn Service filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Vpn Service"
          placeholder="code, name or description"
          value={value.q ?? ''}
          onChange={(v) => patch({ q: v || undefined })}
        />
      </FilterField>
      <FilterField label="Status">
        <MultiSelect
          ariaLabel="Status filter"
          options={LIFECYCLE_OPTIONS}
          value={value.status ?? []}
          onChange={(v) => patch({ status: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Code">
        <Input
          aria-label="Code filter"
          value={value.code ?? ''}
          onChange={(v) => patch({ code: v || undefined })}
        />
      </FilterField>
      <FilterField label="Name">
        <Input
          aria-label="Name filter"
          value={value.name ?? ''}
          onChange={(v) => patch({ name: v || undefined })}
        />
      </FilterField>
      <FilterField label="External Ref">
        <Input
          aria-label="External Ref filter"
          value={value.externalRef ?? ''}
          onChange={(v) => patch({ externalRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vpn Name">
        <Input
          aria-label="Vpn Name filter"
          value={value.vpnName ?? ''}
          onChange={(v) => patch({ vpnName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vpn Type">
        <MultiSelect
          ariaLabel="Vpn Type filter"
          options={VPN_SERVICE_VPN_TYPE_OPTIONS}
          value={value.vpnType ?? []}
          onChange={(v) => patch({ vpnType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Vrf Id">
        <Input
          aria-label="Vrf Id filter"
          placeholder="UUID"
          value={value.vrfId ?? ''}
          onChange={(v) => patch({ vrfId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Customer Id">
        <Input
          aria-label="Customer Id filter"
          placeholder="UUID"
          value={value.customerId ?? ''}
          onChange={(v) => patch({ customerId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Topology">
        <MultiSelect
          ariaLabel="Topology filter"
          options={VPN_SERVICE_TOPOLOGY_OPTIONS}
          value={value.topology ?? []}
          onChange={(v) => patch({ topology: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Owner">
        <Input
          aria-label="Owner filter"
          value={value.owner ?? ''}
          onChange={(v) => patch({ owner: v || undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={VPN_SERVICE_CRITICALITY_OPTIONS}
          value={value.criticality ?? []}
          onChange={(v) => patch({ criticality: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Cost Center">
        <Input
          aria-label="Cost Center filter"
          value={value.costCenter ?? ''}
          onChange={(v) => patch({ costCenter: v || undefined })}
        />
      </FilterField>
      <FilterField label="Effective From">
        <DateRange
          ariaLabel="Effective From"
          from={value.effectiveFromFrom}
          to={value.effectiveFromTo}
          onChange={(from, to) => patch({ effectiveFromFrom: from, effectiveFromTo: to })}
        />
      </FilterField>
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
