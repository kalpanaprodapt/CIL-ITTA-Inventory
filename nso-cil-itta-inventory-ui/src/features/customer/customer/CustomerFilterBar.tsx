import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CUSTOMER_SEGMENT_OPTIONS } from './types';
import type { CustomerFilter } from './types';

interface Props {
  value: CustomerFilter;
  onChange: (next: CustomerFilter) => void;
  onReset: () => void;
}

export function CustomerFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CustomerFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Customer filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Customer"
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
      <FilterField label="Legal Name">
        <Input
          aria-label="Legal Name filter"
          value={value.legalName ?? ''}
          onChange={(v) => patch({ legalName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Segment">
        <MultiSelect
          ariaLabel="Segment filter"
          options={CUSTOMER_SEGMENT_OPTIONS}
          value={value.segment ?? []}
          onChange={(v) => patch({ segment: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Tax Id">
        <Input
          aria-label="Tax Id filter"
          value={value.taxId ?? ''}
          onChange={(v) => patch({ taxId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Industry">
        <Input
          aria-label="Industry filter"
          value={value.industry ?? ''}
          onChange={(v) => patch({ industry: v || undefined })}
        />
      </FilterField>
      <FilterField label="Account Manager">
        <Input
          aria-label="Account Manager filter"
          value={value.accountManager ?? ''}
          onChange={(v) => patch({ accountManager: v || undefined })}
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
