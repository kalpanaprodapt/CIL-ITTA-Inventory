import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';

import type { SubInterfaceFilter } from './types';

interface Props {
  value: SubInterfaceFilter;
  onChange: (next: SubInterfaceFilter) => void;
  onReset: () => void;
}

export function SubInterfaceFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: SubInterfaceFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Sub Interface filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Sub Interface"
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
      <FilterField label="Interface Id">
        <Input
          aria-label="Interface Id filter"
          placeholder="UUID"
          value={value.interfaceId ?? ''}
          onChange={(v) => patch({ interfaceId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Vlan Tag">
        <RangeInputs
          ariaLabel="Vlan Tag"
          min={value.vlanTagMin}
          max={value.vlanTagMax}
          onChange={(min, max) => patch({ vlanTagMin: min, vlanTagMax: max })}
        />
      </FilterField>
      <FilterField label="Encapsulation">
        <Input
          aria-label="Encapsulation filter"
          value={value.encapsulation ?? ''}
          onChange={(v) => patch({ encapsulation: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ipv4 Address">
        <Input
          aria-label="Ipv4 Address filter"
          value={value.ipv4Address ?? ''}
          onChange={(v) => patch({ ipv4Address: v || undefined })}
        />
      </FilterField>
      <FilterField label="Ipv6 Address">
        <Input
          aria-label="Ipv6 Address filter"
          value={value.ipv6Address ?? ''}
          onChange={(v) => patch({ ipv6Address: v || undefined })}
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
