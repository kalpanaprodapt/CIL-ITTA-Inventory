import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { IP_ADDRESS_ASSIGNMENT_TYPE_OPTIONS } from './types';
import type { IpAddressFilter } from './types';

interface Props {
  value: IpAddressFilter;
  onChange: (next: IpAddressFilter) => void;
  onReset: () => void;
}

export function IpAddressFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: IpAddressFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Ip Address filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Ip Address"
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
      <FilterField label="Subnet Id">
        <Input
          aria-label="Subnet Id filter"
          placeholder="UUID"
          value={value.subnetId ?? ''}
          onChange={(v) => patch({ subnetId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Address">
        <Input
          aria-label="Address filter"
          value={value.address ?? ''}
          onChange={(v) => patch({ address: v || undefined })}
        />
      </FilterField>
      <FilterField label="Assigned To Id">
        <Input
          aria-label="Assigned To Id filter"
          placeholder="UUID"
          value={value.assignedToId ?? ''}
          onChange={(v) => patch({ assignedToId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Assignment Type">
        <MultiSelect
          ariaLabel="Assignment Type filter"
          options={IP_ADDRESS_ASSIGNMENT_TYPE_OPTIONS}
          value={value.assignmentType ?? []}
          onChange={(v) => patch({ assignmentType: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Dns Name">
        <Input
          aria-label="Dns Name filter"
          value={value.dnsName ?? ''}
          onChange={(v) => patch({ dnsName: v || undefined })}
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
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
        />
      </FilterField>
      <FilterField label="Labels">
        <Input
          aria-label="Labels filter"
          value={value.labels ?? ''}
          onChange={(v) => patch({ labels: v || undefined })}
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
