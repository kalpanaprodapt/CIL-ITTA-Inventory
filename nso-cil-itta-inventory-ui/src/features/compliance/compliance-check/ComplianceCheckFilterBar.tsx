import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { COMPLIANCE_CHECK_RESULT_OPTIONS, COMPLIANCE_CHECK_CRITICALITY_OPTIONS } from './types';
import type { ComplianceCheckFilter } from './types';

interface Props {
  value: ComplianceCheckFilter;
  onChange: (next: ComplianceCheckFilter) => void;
  onReset: () => void;
}

export function ComplianceCheckFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ComplianceCheckFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Compliance Check filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Compliance Check"
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
      <FilterField label="Rule Id">
        <Input
          aria-label="Rule Id filter"
          placeholder="UUID"
          value={value.ruleId ?? ''}
          onChange={(v) => patch({ ruleId: v || undefined })}
        />
      </FilterField>
      <FilterField label="Target Ref">
        <Input
          aria-label="Target Ref filter"
          value={value.targetRef ?? ''}
          onChange={(v) => patch({ targetRef: v || undefined })}
        />
      </FilterField>
      <FilterField label="Checked At">
        <DateRange
          ariaLabel="Checked At"
          from={value.checkedAtFrom}
          to={value.checkedAtTo}
          onChange={(from, to) => patch({ checkedAtFrom: from, checkedAtTo: to })}
        />
      </FilterField>
      <FilterField label="Result">
        <MultiSelect
          ariaLabel="Result filter"
          options={COMPLIANCE_CHECK_RESULT_OPTIONS}
          value={value.result ?? []}
          onChange={(v) => patch({ result: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Criticality">
        <MultiSelect
          ariaLabel="Criticality filter"
          options={COMPLIANCE_CHECK_CRITICALITY_OPTIONS}
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
      <FilterField label="Effective To">
        <DateRange
          ariaLabel="Effective To"
          from={value.effectiveToFrom}
          to={value.effectiveToTo}
          onChange={(from, to) => patch({ effectiveToFrom: from, effectiveToTo: to })}
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
