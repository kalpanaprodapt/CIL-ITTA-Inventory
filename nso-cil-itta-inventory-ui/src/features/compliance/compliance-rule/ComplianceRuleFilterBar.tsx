import { DateRange, FilterField, Input, MultiSelect, RangeInputs, TriState } from '@/shared/components/filters';
import { LIFECYCLE_OPTIONS } from '@/shared/types';
import { COMPLIANCE_RULE_FRAMEWORK_OPTIONS, COMPLIANCE_RULE_SEVERITY_OPTIONS, COMPLIANCE_RULE_CRITICALITY_OPTIONS } from './types';
import type { ComplianceRuleFilter } from './types';

interface Props {
  value: ComplianceRuleFilter;
  onChange: (next: ComplianceRuleFilter) => void;
  onReset: () => void;
}

export function ComplianceRuleFilterBar({ value, onChange, onReset }: Props) {
  const patch = (partial: ComplianceRuleFilter) => onChange({ ...value, ...partial });

  return (
    <form className="filter-bar" aria-label="Compliance Rule filters" onSubmit={(ev) => ev.preventDefault()}>
      <FilterField label="Search">
        <Input
          aria-label="Search Compliance Rule"
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
      <FilterField label="Rule Code">
        <Input
          aria-label="Rule Code filter"
          value={value.ruleCode ?? ''}
          onChange={(v) => patch({ ruleCode: v || undefined })}
        />
      </FilterField>
      <FilterField label="Framework">
        <MultiSelect
          ariaLabel="Framework filter"
          options={COMPLIANCE_RULE_FRAMEWORK_OPTIONS}
          value={value.framework ?? []}
          onChange={(v) => patch({ framework: v.length ? v : undefined })}
        />
      </FilterField>
      <FilterField label="Severity">
        <MultiSelect
          ariaLabel="Severity filter"
          options={COMPLIANCE_RULE_SEVERITY_OPTIONS}
          value={value.severity ?? []}
          onChange={(v) => patch({ severity: v.length ? v : undefined })}
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
          options={COMPLIANCE_RULE_CRITICALITY_OPTIONS}
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
