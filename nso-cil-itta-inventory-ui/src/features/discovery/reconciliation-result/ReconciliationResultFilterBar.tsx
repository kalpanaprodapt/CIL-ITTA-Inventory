import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { RECONCILIATION_RESULT_OUTCOME_OPTIONS, RECONCILIATION_RESULT_CRITICALITY_OPTIONS } from './types';
import type { ReconciliationResultFilter } from './types';

interface Props {
  value: ReconciliationResultFilter;
  onChange: (next: ReconciliationResultFilter) => void;
  onReset: () => void;
}

export function ReconciliationResultFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ReconciliationResultFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Reconciliation Result filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Reconciliation Result"
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
      <FilterField label="Job Id">
        <Input
          aria-label="Job Id filter"
          placeholder="UUID"
          value={value.jobId ?? ''}
          onChange={(v) => patch({ jobId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Rule Id">
        <Input
          aria-label="Rule Id filter"
          placeholder="UUID"
          value={value.ruleId ?? ''}
          onChange={(v) => patch({ ruleId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Outcome">
        <MultiSelect
          ariaLabel="Outcome filter"
          options={RECONCILIATION_RESULT_OUTCOME_OPTIONS}
          value={value.outcome ?? []}
          onChange={(v) => patch({ outcome: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Resolved By">
        <Input
          aria-label="Resolved By filter"
          value={value.resolvedBy ?? ''}
          onChange={(v) => patch({ resolvedBy: v || undefined })}
        />
      </FilterField>
      <FilterField label="Source System">
        <Input
          aria-label="Source System filter"
          value={value.sourceSystem ?? ''}
          onChange={(v) => patch({ sourceSystem: v || undefined })}
        />
      </FilterField>
      <FilterField label="Region">
        <Input
          aria-label="Region filter"
          value={value.region ?? ''}
          onChange={(v) => patch({ region: v || undefined })}
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
          options={RECONCILIATION_RESULT_CRITICALITY_OPTIONS}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
