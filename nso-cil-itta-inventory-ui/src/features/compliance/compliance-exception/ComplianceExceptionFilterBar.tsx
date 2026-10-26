import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_OPTIONS } from './types';
import type { ComplianceExceptionFilter } from './types';

interface Props {
  value: ComplianceExceptionFilter;
  onChange: (next: ComplianceExceptionFilter) => void;
  onReset: () => void;
}

export function ComplianceExceptionFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ComplianceExceptionFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Compliance Exception filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Compliance Exception"
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
      <FilterField label="Approved By">
        <Input
          aria-label="Approved By filter"
          value={value.approvedBy ?? ''}
          onChange={(v) => patch({ approvedBy: v || undefined })}
        />
      </FilterField>
      <FilterField label="Expires On">
        <DateRange
          ariaLabel="Expires On"
          from={value.expiresOnFrom}
          to={value.expiresOnTo}
          onChange={(from, to) => patch({ expiresOnFrom: from, expiresOnTo: to })}
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
      <FilterField label="Lifecycle Stage">
        <MultiSelect
          ariaLabel="Lifecycle Stage filter"
          options={COMPLIANCE_EXCEPTION_LIFECYCLE_STAGE_OPTIONS}
          value={value.lifecycleStage ?? []}
          onChange={(v) => patch({ lifecycleStage: v.length ? v : undefined })}
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
      <div className="filter-bar__actions">
        <button type="button" className="btn btn--ghost" onClick={onReset}>
          Reset filters
        </button>
      </div>
    </form>
  );
}
