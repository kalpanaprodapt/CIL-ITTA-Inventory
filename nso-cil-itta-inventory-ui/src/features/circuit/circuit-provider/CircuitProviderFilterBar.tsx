import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { CIRCUIT_PROVIDER_PROVIDER_TYPE_OPTIONS, CIRCUIT_PROVIDER_CRITICALITY_OPTIONS } from './types';
import type { CircuitProviderFilter } from './types';

interface Props {
  value: CircuitProviderFilter;
  onChange: (next: CircuitProviderFilter) => void;
  onReset: () => void;
}

export function CircuitProviderFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: CircuitProviderFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Circuit Provider filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Circuit Provider"
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
      <FilterField label="Provider Name">
        <Input
          aria-label="Provider Name filter"
          value={value.providerName ?? ''}
          onChange={(v) => patch({ providerName: v || undefined })}
        />
      </FilterField>
      <FilterField label="Account Number">
        <Input
          aria-label="Account Number filter"
          value={value.accountNumber ?? ''}
          onChange={(v) => patch({ accountNumber: v || undefined })}
        />
      </FilterField>
      <FilterField label="Noc">
        <Input
          aria-label="Noc filter"
          value={value.noc ?? ''}
          onChange={(v) => patch({ noc: v || undefined })}
        />
      </FilterField>
      <FilterField label="Escalation Email">
        <Input
          aria-label="Escalation Email filter"
          value={value.escalationEmail ?? ''}
          onChange={(v) => patch({ escalationEmail: v || undefined })}
        />
      </FilterField>
      <FilterField label="Provider Type">
        <MultiSelect
          ariaLabel="Provider Type filter"
          options={CIRCUIT_PROVIDER_PROVIDER_TYPE_OPTIONS}
          value={value.providerType ?? []}
          onChange={(v) => patch({ providerType: v.length ? v : undefined })}
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
          options={CIRCUIT_PROVIDER_CRITICALITY_OPTIONS}
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
