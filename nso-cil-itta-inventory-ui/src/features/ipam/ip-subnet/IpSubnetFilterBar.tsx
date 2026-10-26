import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { IP_SUBNET_IP_VERSION_OPTIONS, IP_SUBNET_CRITICALITY_OPTIONS } from './types';
import type { IpSubnetFilter } from './types';

interface Props {
  value: IpSubnetFilter;
  onChange: (next: IpSubnetFilter) => void;
  onReset: () => void;
}

export function IpSubnetFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: IpSubnetFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Ip Subnet filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Ip Subnet"
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
      <FilterField label="Cidr">
        <Input
          aria-label="Cidr filter"
          value={value.cidr ?? ''}
          onChange={(v) => patch({ cidr: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ip Version">
        <MultiSelect
          ariaLabel="Ip Version filter"
          options={IP_SUBNET_IP_VERSION_OPTIONS}
          value={value.ipVersion ?? []}
          onChange={(v) => patch({ ipVersion: v.length ? v : undefined })}
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
      <FilterField label="Gateway">
        <Input
          aria-label="Gateway filter"
          value={value.gateway ?? ''}
          onChange={(v) => patch({ gateway: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vlan Id">
        <Input
          aria-label="Vlan Id filter"
          placeholder="UUID"
          value={value.vlanId ?? ''}
          onChange={(v) => patch({ vlanId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Utilization Pct">
        <RangeInputs
          ariaLabel="Utilization Pct"
          min={value.utilizationPctMin}
          max={value.utilizationPctMax}
          onChange={(min, max) => patch({ utilizationPctMin: min, utilizationPctMax: max })}
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
          options={IP_SUBNET_CRITICALITY_OPTIONS}
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
